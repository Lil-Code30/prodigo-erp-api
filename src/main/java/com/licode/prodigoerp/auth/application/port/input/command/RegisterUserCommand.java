package com.licode.prodigoerp.auth.application.port.input.command;


import com.licode.prodigoerp.module.domain.command.SelectedModuleCommand;

import java.util.List;

public record RegisterUserCommand(String companyName,
                                  String companySlug,
                                  String country,
                                  String username,
                                  String email,
                                  String password,
                                  String firstName,
                                  String lastName,
                                  List<SelectedModuleCommand> selectedModules) {
}
