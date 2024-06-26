package org.example.dbHibernate;

import org.example.DAO.Course;
import org.example.DAO.CourseDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
//		ClassPathXmlApplicationContext context =
//				new ClassPathXmlApplicationContext("applicationContext.xml");


       AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CourseDAO courseDao = context.getBean(CourseDAO.class);


//		Course spring = new Course();
//		spring.setTitle("Spring");
//		spring.setLength(40);
//		spring.setDescription("Spring framework");
//		courseDao.insert(spring);

        //courseDao.delete(8);


//		for(Course c : courseDao.findAll())
//			System.out.println(c);

        //System.out.println(courseDao.findById(5));

        for (Course c : courseDao.findByTitle("web"))
            System.out.println(c);

        context.close();
    }
}
