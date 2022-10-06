package kr.sproutfx.oauth.backoffice.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.sproutfx.oauth.backoffice.common.base.BaseException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StructuredBody {
    private boolean succeeded;
    private Object content;
    private Error error;

    private StructuredBody(Object content) {
        this.succeeded = true;
        this.content = content;
        this.error = null;
    }

    private StructuredBody() {
        this.succeeded = true;
        this.content = null;
        this.error = null;
    }

    private StructuredBody(Throwable error) {
        this.succeeded = false;
        this.content = null;
        this.error = new Error(((BaseException) error).getValue(), ((BaseException) error).getReason());
    }

    public static StructuredBody content(Object content) {
        return new StructuredBody(content);
    }

    public static StructuredBody noContent() {
        return new StructuredBody();
    }

    public static StructuredBody error(Throwable error) {
        return new StructuredBody(error);
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public Object getContent() {
        return content;
    }

    public <D> D getContent(Class<D> clazz) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(this.content, clazz);
    }

    public <S, D> D getContent(Class<D> clazz, PropertyMap<S, D> propertyMap) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);

        return modelMapper.map(this.content, clazz);
    }

    public Error getError() {
        return error;
    }

    static class Error {
        private final String value;
        private final String reason;

        public Error(String value, String reason) {
            this.value = value;
            this.reason = reason;
        }

        public String getValue() {
            return value;
        }

        public String getReason() {
            return reason;
        }
    }
}
