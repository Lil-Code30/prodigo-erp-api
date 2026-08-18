package com.licode.prodigoerp.auth.domain.command;


public record RegisterUserCommand(String companyName,
                                  String companySlug,
                                  String country,
                                  String username,
                                  String email,
                                  String password,
                                  String firstName,
                                  String lastName,
                                  SelectedModuleCommand selectedModule) {
}
