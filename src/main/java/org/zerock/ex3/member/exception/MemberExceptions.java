package org.zerock.ex3.member.exception;


public enum MemberExceptions {

  NOT_FOUND("NOT_FOUND",404),
  DUPLICATE("DUPLICATE",409),
  INVALID("INVALID", 400);

  private MemberTaskException memberTaskException;

  MemberExceptions(String msg, int code) {
    memberTaskException = new MemberTaskException(msg, code);
  }

  public MemberTaskException get() {
    return memberTaskException;
  }
}
