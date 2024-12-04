package dungeonmania.response.models;

import lombok.Getter;

public final class GenericResponseWrapper<T> {
    @Getter
    private final T result;
    @Getter
    private final String errorTitle;
    @Getter
    private final String errorMessage;
    private final boolean isError;

    private GenericResponseWrapper(T result) {
        this.result = result;
        this.errorTitle = this.errorMessage = null;
        this.isError = false;
    }

    private GenericResponseWrapper(String title, String msg) {
        this.result = null;
        this.errorTitle = title;
        this.errorMessage = msg;
        this.isError = true;
    }

    public static <T> GenericResponseWrapper<T> Ok(T result) {
        return new GenericResponseWrapper<>(result);
    }

    public static <T> GenericResponseWrapper<T> Err(Exception e) {
        return new GenericResponseWrapper<>(e.getClass().getSimpleName(), e.getLocalizedMessage());
    }

    public boolean isError() {
        return isError;
    }

}
