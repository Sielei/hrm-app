package com.hrmapp.user.application.port.input.handler.query;

import com.hrmapp.user.application.dto.PageQuery;
import com.hrmapp.user.application.dto.PagedResult;
import com.hrmapp.user.application.dto.PasswordPolicyDto;
import com.hrmapp.user.application.port.output.PasswordPolicyRepository;
import com.hrmapp.user.domain.exception.PasswordPolicyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional(readOnly = true)
public class PasswordPolicyQueryHandler {
    private final PasswordPolicyRepository passwordPolicyRepository;

    public PasswordPolicyQueryHandler(PasswordPolicyRepository passwordPolicyRepository) {
        this.passwordPolicyRepository = passwordPolicyRepository;
    }

    public PasswordPolicyDto findPasswordPolicyById(UUID passwordPolicyId) {
        return passwordPolicyRepository.findById(passwordPolicyId)
                .map(PasswordPolicyDto::fromEntity)
                .orElseThrow(() -> new PasswordPolicyException("Password policy with id: " +
                        passwordPolicyId + " does not exist!"));
    }

    public PagedResult<PasswordPolicyDto> findAllPasswordPolicies(PageQuery pageQuery) {
        var pageNo = pageQuery.pageNo() > 0 ? pageQuery.pageNo() - 1 : 0;
        var pageable = PageRequest.of(pageNo, pageQuery.pageSize());
        var page = passwordPolicyRepository.findPasswordPolicies(pageable);
        return new PagedResult<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
