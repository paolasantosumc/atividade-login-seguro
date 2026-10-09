package br.com.loginseguro.model;

public enum Role {
	ADMIN("Administrador", "Gerencia usuários e permissões do sistema."),
	MANAGER("Gestor", "Acompanha e administra as atividades de gestão."),
	USER("Usuário", "Acessa o painel e os recursos disponíveis para sua conta.");

	private final String label;
	private final String description;

	Role(String label, String description) {
		this.label = label;
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public String getDescription() {
		return description;
	}
}
