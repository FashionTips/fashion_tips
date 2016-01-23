package com.bionicuniversity.edu.fashiontips.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

/**
 * Spring MVC configuration.
 *
 * @author Maksym Dolia
 * @since 11.12.2015.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.bionicuniversity.edu.fashiontips.web.controllers")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("homepage");
        registry.addViewController("/user/*").setViewName("user.profile");
        registry.addViewController("/user/edit").setViewName("edit.user.profile");
        registry.addViewController("/post").setViewName("post.add");
        registry.addViewController("/post/*").setViewName("post.view");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.tiles();
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer configurer = new TilesConfigurer();
        configurer.setDefinitions("/WEB-INF/defs/app-tiles.xml");
        configurer.setCheckRefresh(true);
        return configurer;
    }
}
