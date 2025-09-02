package org.rookie.model.form;

import lombok.Data;

import java.util.List;

@Data
public class RoleForm {

    String name;

    String description;

    List<Long> permissionIds;

}
