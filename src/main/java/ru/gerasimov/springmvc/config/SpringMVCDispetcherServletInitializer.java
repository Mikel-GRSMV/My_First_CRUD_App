package ru.gerasimov.springmvc.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class SpringMVCDispetcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    //в этих классах мы должны передать какую ту конфигурацию
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};//Теперь он знает где находится spring конфигурация
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    //фильтр для _method
    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext); //здесь выполняем приватный метод
    }

    private void registerHiddenFieldFilter(ServletContext aContext) { // приватный метод в нем добавляем фильтр
        //он как раз и смотрит на _method
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null, true, "/*");
    }
}