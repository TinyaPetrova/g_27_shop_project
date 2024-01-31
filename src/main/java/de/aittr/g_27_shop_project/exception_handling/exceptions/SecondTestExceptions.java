package de.aittr.g_27_shop_project.exception_handling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// второй способ - простановка аннотации непосредственно на классе исключения
// минус - не позволяет отправить клиенту информативное сообщение
// плюс - это глобальный обработчик  реагирует на исключения, выброшенные в любом классе проекта

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
public class SecondTestExceptions extends RuntimeException {

  public SecondTestExceptions(String message) {
    super(message);
  }
}
