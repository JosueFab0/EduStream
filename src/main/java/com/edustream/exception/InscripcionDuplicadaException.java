package com.edustream.exception;

public final class InscripcionDuplicadaException extends NegocioException {
  public InscripcionDuplicadaException() {
    super("Ya estás inscrito en este curso.");
  }
}
