package de.aittr.g_27_shop_project.exception_handling;

import de.aittr.g_27_shop_project.exception_handling.exceptions.FourthTestException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductCalculationException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductDeletingException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductGettingException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductNotFoundException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductSavingException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductUpdatingException;
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

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<Response> handleProductNotFoundException(ProductNotFoundException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ProductSavingException.class)
  public ResponseEntity<Response> handleProductSavingException(ProductSavingException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ProductGettingException.class)
  public ResponseEntity<Response> handleProductRetrievalException(ProductGettingException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.I_AM_A_TEAPOT);
  }

  @ExceptionHandler(ProductUpdatingException.class)
  public ResponseEntity<Response> handleProductUpdateException(ProductUpdatingException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ProductDeletingException.class)
  public ResponseEntity<Response> handleProductDeletionException(ProductDeletingException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ProductCalculationException.class)
  public ResponseEntity<Response> handleProductCalculationException(ProductCalculationException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
