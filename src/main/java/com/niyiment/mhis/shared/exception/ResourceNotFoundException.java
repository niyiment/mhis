package com.niyiment.mhis.shared.exception;


import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
  private final String resourceType;
  private final Object resourceId;

  public ResourceNotFoundException(String message) {
    super(message);
    this.resourceType = null;
    this.resourceId = null;
  }

  public ResourceNotFoundException(String resourceType, Object resourceId) {
    super(String.format("%s not found with id: %s", resourceType, resourceId));
    this.resourceType = resourceType;
    this.resourceId = resourceId;
  }

  public ResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
    this.resourceType = null;
    this.resourceId = null;
  }
}
