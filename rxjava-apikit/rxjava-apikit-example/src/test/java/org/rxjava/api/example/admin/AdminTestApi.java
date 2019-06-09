package org.rxjava.api.example.admin;

import reactor.core.publisher.Mono;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.rxjava.apikit.client.*;

import org.rxjava.api.example.form.TestForm;

public class AdminTestApi {
	private ClientAdapter clientAdapter;

	public AdminTestApi() {
	}

	public AdminTestApi(ClientAdapter clientAdapter) {
		this.clientAdapter = clientAdapter;
	}

	public Mono<Integer> testPath(String id, TestForm form) {
		Map<String, Object> _uriVariables = new HashMap<>();
		_uriVariables.put("id", id);
		String _url = ApiUtils.expandUriComponent("dashboard/testPath/{id}", _uriVariables);

		List<Entry<String, Object>> _form = form.encode("", new ArrayList<>());
		return clientAdapter.request("GET", _url, _form, _0Type);
	}

	public void setclientAdapter(ClientAdapter clientAdapter) {
		this.clientAdapter = clientAdapter;
	}

	public ClientAdapter getclientAdapter() {
		return clientAdapter;
	}

	private static final ApiType _0Type = ApiUtils.type(Integer.class);
}