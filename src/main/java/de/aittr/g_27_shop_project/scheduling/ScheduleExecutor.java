package de.aittr.g_27_shop_project.scheduling;

import de.aittr.g_27_shop_project.domain.jpa.Task;
import de.aittr.g_27_shop_project.services.jpa.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component // аннотация для помещения класса в спринг контекст
@EnableScheduling // включает в нашем прил планировщика задач в момент загрузки этого класса
public class ScheduleExecutor {

  private static Logger logger = LoggerFactory.getLogger(ScheduleExecutor.class);
  private TaskService taskService;

  public ScheduleExecutor(TaskService taskService) {
    this.taskService = taskService;
  }

//  @Scheduled(fixedDelay = 5000) // в милисекундах
//  public void fixedDelayTask() {
//    taskService.createTask("Fixed delay task");
//    try {
//      Thread.sleep(3000);
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//  }

//  @Scheduled(fixedDelay = 5000) // в милисекундах
//  public void fixedDelayTask() {
//    taskService.createTask("Fixed delay task 3000 ms");
//    try {
//      Thread.sleep(3000);
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//  }

//  @Scheduled(fixedRate = 5000) // в милисекундах
//  public void fixedRateTask() {
//    taskService.createTask("Fixed rate task 7000 ms");
//    try {
//      Thread.sleep(7000);
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//  }

  //  @Scheduled(fixedRate = 5000) // в милисекундах
//  public void fixedRateAsyncTask() {
//    taskService.createTask("Fixed rate async task 7000 ms");
//    try {
//      Thread.sleep(7000);
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//  @Scheduled(fixedRate = 5000) // в милисекундах
//  public void fixedRateAsyncTask() {
//    taskService.createTask("Fixed rate async task 7000 ms");
//    try {
//      Thread.sleep(7000);
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//  }

//  @Scheduled(fixedDelay = 5000, initialDelay = 20000) // в милисекундах
//  public void initialDelayTask() {
//    taskService.createTask("Initial delay task");
//    try {
//      Thread.sleep(1000);
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//  }

  // 2 часа - 7 200 000 милисек
  // 7200000 -> PT02H = period of time 02 hours

//  @Scheduled(fixedDelayString = "PT03S") // 3 сек
//  public void anotherDelayFormat() {
//    taskService.createTask("Another delay format task");
//  }

//  @Scheduled(fixedDelayString = "${interval}") // 3 сек
//  public void delayInPropertyTask() {
//    taskService.createTask("Delay in property task");
//    try {
//      Thread.sleep(2000);
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//  }

  // 55 * * * * * - задача будет отрабатывает каждые 55 сек каждой минуты каждого часа и тд
  // это крон выражение - специальный макрос, который используется для обознач времени

  // 0 0 8,10 * * * - каждый день в 8:00 и 10:00 часов
  // 0 15 9-17 * * MON-FRI - в будни с 9 до 17 в 15 мин 0 сек: 9:15, 10:15... 17:15

//  @Scheduled(cron = "5-10 * * * * *")  // задачи выполняются в промежутке 5-10 сек каждой минуты каждого часа тд
//  public void cronExpressionTask() {
//    taskService.createTask("Cron expression task");
//  }

  // планируем выполнение метода getAllActiveProducts() в классе JpaProductsService
  // задача выполнится по триггеру, т.е. в нашем случае
  // надо вызвать всех юзеров в постмане,
  // и тогда задача будет выполняться с заданной периодичностью пока запущена прилага
  public static void scheduleTaskExecution(Task task) {
    TaskScheduler scheduler = new DefaultManagedTaskScheduler();
    scheduler.schedule(
        () -> logger.info(task.getDescription()),
        new CronTrigger("0,10,20,30,40,50 * * * * *")
    );
  }
}
