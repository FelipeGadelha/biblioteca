package br.com.portfolio.biblioteca.api.handler;

import java.util.Map;
import java.util.Set;

public class ValidationExceptionDetails extends ExceptionDetails{

	private final Map<String, Set<String>> fieldError;
	
	protected ValidationExceptionDetails(Builder builder) {
		super(builder);
		this.fieldError = builder.fieldError;
		
	}

	public static Builder builder() {
        return new Builder();
    }
	
	public static class Builder extends ExceptionDetails.Builder<Builder> {
		
		private Map<String, Set<String>> fieldError;
		
		protected Builder() {
			super();
		}
		
		public Builder fieldError(Map<String, Set<String>> map) {
			this.fieldError = map;
			return this;
		}

		@Override
		public Builder getThis() {
			return this;
		}
		
		@Override
		public ValidationExceptionDetails build() {
            return new ValidationExceptionDetails(this);
        }
	}
	
	public Map<String, Set<String>> getFieldError() {
		return fieldError;
	}
}
