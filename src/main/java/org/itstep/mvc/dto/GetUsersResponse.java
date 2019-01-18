package org.itstep.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.itstep.mvc.entities.User;

import java.util.List;

@Data
@AllArgsConstructor
public class GetUsersResponse {
    private String status;
    private String message;
    private List<User> data;
}
