package com.example.obhibernateproyecto;

import com.example.obhibernateproyecto.entities.BillingInfo;
import com.example.obhibernateproyecto.entities.Task;
import com.example.obhibernateproyecto.entities.User;
import com.example.obhibernateproyecto.repository.BillingInfoRepository;
import com.example.obhibernateproyecto.repository.TaskRepository;
import com.example.obhibernateproyecto.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class ObHibernateProyectoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ObHibernateProyectoApplication.class, args);

		// Billing Info y User ======================================
		BillingInfoRepository billingInfoRepository =
				context.getBean(BillingInfoRepository.class);

		UserRepository userRepository =
				context.getBean(UserRepository.class);


		BillingInfo info1 = new BillingInfo(null, "ELM street", "55432", "Pesadilla", "Transilvania", "ES543254325432", null);
		billingInfoRepository.save(info1);

		User user1 = new User(null, "Jack", "Dorsey", "88877765R", false, LocalDate.of(1970, 12, 1));
		user1.setBillingInfo(info1);
		userRepository.save(user1);

		// Task y User ======================================
		User user2 = new User(null, "Mike", "Dorsey", "4567892E", true, LocalDate.of(1970, 12, 1));
		userRepository.save(user2);

		TaskRepository taskRepository = context.getBean(TaskRepository.class);
		Task task1 = new Task(null, "Tarea 1", "Lorem ipsum", false, LocalDate.of(2022, 1, 1), user1);
		Task task2 = new Task(null, "Tarea 2", "Lorem ipsum", true, LocalDate.of(2022, 2, 1), user1);

		Task task3 = new Task(null, "Tarea 3", "Lorem ipsum", false, LocalDate.of(2022, 3, 1), user2);
		Task task4 = new Task(null, "Tarea 4", "Lorem ipsum", false, LocalDate.of(2022, 3, 1), user2);

		taskRepository.saveAll(List.of(task1, task2, task3, task4));

		System.out.println("fin");
	}

}
