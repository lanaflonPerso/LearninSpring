package ru.ivmiit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("ru.ivmiit\\context.xml"); // inversion of control container

        //MessageRenderer messageRenderer = (MessageRenderer) context.getBean("renderer");

    //    messageRenderer.printMessage();

        IndependentMessageRenderer renderer = context.getBean(IndependentMessageRenderer.class);

        renderer.print();
    }
}
