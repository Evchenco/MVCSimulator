package com.simulator.filter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.reflections.Reflections;

import com.simulator.annotation.Controller;
import com.simulator.annotation.mapping.PathVariable;
import com.simulator.annotation.mapping.RequestMapping;
import com.simulator.exception.UnsupportedBehavior;
import com.simulator.model.Model;

/**
 * Servlet Filter implementation class MainFilter
 */
@WebFilter("/*")
public class FrontFilter implements Filter {

	private HashMap<String, Method> controllerSet = new HashMap<String, Method>();

	/**
	 * Default constructor.
	 */
	public FrontFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (request instanceof HttpServletRequest) {
			String url = ((HttpServletRequest) request).getRequestURL().toString();
			String uri = ((HttpServletRequest) request).getRequestURI().toString()
					.replace("/MVCSimulator", "");
			String queryString = ((HttpServletRequest) request).getQueryString();

			Method method = getMethod(uri);
			if (method == null) {
				chain.doFilter(request, response);
				return;
			}
			Object[] param = getParam(method, uri);

			try {
				Object newInstance = method.getDeclaringClass().newInstance();
				// Model model = new Model();
				Object o = method.invoke(newInstance, param);

				if (o == null)
					throw new UnsupportedBehavior();

				Map<String, Object> params = ((Model) (param[param.length - 1])).getParams();
				for (String key : params.keySet()) {
					((HttpServletRequest) request).setAttribute(key, params.get(key));
				}

				RequestDispatcher rd = request.getRequestDispatcher(o + ".jsp");
				rd.forward(request, response);

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| InstantiationException e) {
				e.printStackTrace();
			}

		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

		Reflections reflections = new Reflections("");
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);

		for (Class<?> controller : annotated) {
			Method[] declaredMethods = controller.getDeclaredMethods();
			for (Method method : declaredMethods) {
				if (method.isAnnotationPresent(RequestMapping.class)) {
					RequestMapping rm = method.getAnnotation(RequestMapping.class);
					controllerSet.put(rm.value(), method);
				}
			}
		}
	}

	private Method getMethod(String url) {
		if (url == null)
			return null;
		for (String srt : controllerSet.keySet()) {

			String[] splitStr = srt.split("/");
			String[] splitUrl = url.split("/");

			boolean flag = false;
			for (int i = 0; i < splitUrl.length && i < splitStr.length; i++) {
				if (compareURL(splitUrl[i], splitStr[i])) {
					flag = true;
				} else
					continue;
			}
			if (flag || splitUrl.length == splitStr.length)
				return controllerSet.get(srt);
		}
		return null;
	}

	private boolean compareURL(String str, String url) {
		if (str == null || url == null)
			return false;
		if (str.equals(url))
			return true;
		else if (str.startsWith("{") && str.endsWith("}"))
			return true;
		return false;
	}

	private Object[] getParam(Method method, String uri) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		if (parameterTypes == null)
			return new Object[0];
		int length = parameterTypes.length;
		Object[] param = new Object[length];

		for (int i = 0; i < length; i++) {
			Class cl = parameterTypes[i];
			if (i == length - 1 && cl.equals(Model.class)) {
				param[i] = new Model();
			}

			for (int y = 0; y < parameterAnnotations[i].length; y++) {
				Class<? extends Annotation> annotationType = parameterAnnotations[i][y]
						.annotationType();
				if (annotationType.equals(PathVariable.class)) {
					RequestMapping rm = method.getAnnotation(RequestMapping.class);

					// support only integer!
					param[i] = Integer.valueOf(getURLParam(uri, rm.value(),
							((PathVariable) (parameterAnnotations[i][y])).value()));
					break;
				}
			}
		}
		return param;
	}

	private String getURLParam(String url, String strMapping, String paramName) {
		String[] splitStr = strMapping.split("/");
		String[] splitUrl = url.split("/");

		for (int i = 0; i < splitStr.length && i < splitUrl.length; i++) {
			if (splitStr[i].startsWith("{") && splitStr[i].endsWith("}")
					&& splitStr[i].equals("{" + paramName + "}")) {
				return splitUrl[i];
			}
		}
		return null;
	}

}
