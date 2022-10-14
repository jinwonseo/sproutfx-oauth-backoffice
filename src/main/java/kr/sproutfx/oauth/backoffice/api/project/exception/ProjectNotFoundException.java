package kr.sproutfx.oauth.backoffice.api.project.exception;

import kr.sproutfx.oauth.backoffice.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ProjectNotFoundException extends BaseException {

    public ProjectNotFoundException() {
        super("project_not_found", "Project not found", HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
