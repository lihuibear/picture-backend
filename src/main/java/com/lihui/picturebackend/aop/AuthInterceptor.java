package com.lihui.picturebackend.aop;

import com.lihui.picturebackend.annotation.AuthCheck;
import com.lihui.picturebackend.exception.BusinessException;
import com.lihui.picturebackend.exception.ErrorCode;
import com.lihui.picturebackend.model.entity.User;
import com.lihui.picturebackend.model.enums.UserRoleEnum;
import com.lihui.picturebackend.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * 执行拦截
     *
     * @param joinPoint 切入点
     * @param authCheck 权限校验注解
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        String[] mustRoles = authCheck.mustRoles(); // 新增 mustRoles

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 当前登录用户
        User loginUser = userService.getLoginUser(request);
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(loginUser.getUserRole());

        // 没有用户角色，直接拒绝访问
        if (userRoleEnum == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 如果 mustRole 存在，保持原逻辑
        if (mustRole != null && !mustRole.isEmpty()) {
            UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
            if (mustRoleEnum != null && !mustRoleEnum.equals(userRoleEnum)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
            return joinPoint.proceed();
        }

        // 新增逻辑：如果 mustRoles 存在，用户必须属于其中之一
        if (mustRoles != null && mustRoles.length > 0) {
            List<UserRoleEnum> requiredRoles = Arrays.stream(mustRoles)
                    .map(UserRoleEnum::getEnumByValue)
                    .collect(Collectors.toList());
            if (!requiredRoles.contains(userRoleEnum)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
            return joinPoint.proceed();
        }

        // 没有权限要求，直接放行
        return joinPoint.proceed();
    }
}
