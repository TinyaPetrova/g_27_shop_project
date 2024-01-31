package de.aittr.g_27_shop_project.exception_handling;

import de.aittr.g_27_shop_project.exception_handling.exceptions.FourthTestException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ThirdTestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// третий способ - создание класса-адвайса
// плюсы - данный адвайс является глобальным обработчком, который ловит исключения, выброшенные в любом классе проекта
// а также позволяет нам отправлять клиенту любой статус ответа и информативный месседж об ошибке
// минусов почти нет (разве что отдельный класс надо создать)

// пишем класс для того, чтобы глобально в проекте отлавливать исключения + отдавать юзеру месседж об ошибке
@ControllerAdvice
public class CommonAdvice {

  @ExceptionHandler(ThirdTestException.class)
  // объекты этого класса ResponseEntity позволяют отправлять клиенту месседжи + статус ответа
  public ResponseEntity<Response> handleException(ThirdTestException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.I_AM_A_TEAPOT);
  }

  @ExceptionHandler(FourthTestException.class)
  public ResponseEntity<Response> handleException(FourthTestException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
