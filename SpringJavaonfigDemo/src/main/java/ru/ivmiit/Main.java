package ru.ivmiit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class); // inversion of control container

        //MessageRenderer messageRenderer = (MessageRenderer) context.getBean("renderer");

    //    messageRenderer.printMessage();

        IndependentMessageRenderer renderer = context.getBean(IndependentMessageRenderer.class);

        renderer.print();
    }
}
