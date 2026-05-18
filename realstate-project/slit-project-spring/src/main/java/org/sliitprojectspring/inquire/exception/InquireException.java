package org.sliitprojectspring.inquire.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InquireException extends  RuntimeException {

    String message ;


      public InquireException( String message ) {
          super( message);
      }
}
