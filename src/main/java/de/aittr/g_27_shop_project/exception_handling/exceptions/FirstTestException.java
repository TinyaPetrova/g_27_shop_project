package de.aittr.g_27_shop_project.exception_handling.exceptions;

// в реально разработке кастомные исключения должны называться по сути: не первый экспепшн, а что-то типа
// TooSmallAgeException и всё такое. на этом примере мы учимся - допустимо
public class FirstTestException extends RuntimeException {

  public FirstTestException(String message) {
    super(message);
  }
}
