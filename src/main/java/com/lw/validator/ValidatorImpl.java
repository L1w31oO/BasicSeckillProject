package com.lw.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 验证器实现
 */
@Component
// SpringBoot构建电商基础秒杀项目 https://www.imooc.com/learn/1079  3-13 优化校验规则, 说实话没大看懂, 使用第三方插件套路?
//SpringBean初始化完成之后 会回调 ValidatorImpl.afterPropertiesSet()
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    //实现校验方法 并 返回校验结果
    public ValidationResult validate(Object bean) {
        final ValidationResult result = new ValidationResult();
        // 若对应的bean 里边的一些参数规则, 有违背了 ????? constraintViolationSet里边就有这个值
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if (constraintViolationSet.size() > 0) {
            //有错误
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation -> {
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrMsgMap().put(propertyName, errMsg);
            });

        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //用于 包装 javax.validation.Validator 接口
        //将hibernate validator通过工厂的初始化方式 使其 实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

    }

}

