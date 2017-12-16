package br.com.jope.psicologia.controller;

import java.io.Serializable;

public class AbstractController implements Serializable {

	private static final long serialVersionUID = -4808859477511429595L;

	private final String webSocketAddress = "ws://localhost:8080/psicologia-web/pingpong";
}
