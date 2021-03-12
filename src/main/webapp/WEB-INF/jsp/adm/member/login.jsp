<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 로그인</title>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.0.3/tailwind.min.css" />
<link rel="stylesheet" href="/resource/adm/common.css" />
</head>
<body>
	<script>
		const LoginForm__checkAndSubmitDone = false;
		function LoginForm__checkAndSubmit(form) {
			if (LoginForm__checkAndSubmitDone) {
				return;
			}

			form.loginId.value = form.loginId.value.trim();

			if (form.loginId.value.length == 0) {
				alert('로그인아이디를 입력해주세요.');
				form.loginId.focus();

				return;
			}

			if (form.loginPw.value.length == 0) {
				alert('로그인비번을 입력해주세요.');
				form.loginPw.focus();

				return;
			}

			form.submit();
			LoginForm__checkAndSubmitDone = true;
		}
	</script>
	<section
		class="section-login flex items-center min-h-screen bg-white dark:bg-gray-900">
		<div class="container mx-auto">
			<div class="max-w-md mx-auto my-10">
				<div class="text-center">
					<h1
						class="my-3 text-3xl font-semibold text-gray-700 dark:text-gray-200">
						Sing in</h1>
				</div>
				<div class="m-7">
					<form action="doLogin" method="POST"
						onsubmit="LoginForm__checkAndSubmit(this); return false;">
						<div class="mb-6">
							<label class="block mb-2 text-sm text-gray-600 dark:text-gray-400">Id</label>
							<input class="w-full px-3 py-2 placeholder-gray-300 border border-gray-300 rounded-md "
								autofocus="autofocus" type="text" placeholder="id를 입력해주세요."
								name="loginId" maxlength="20" />
						</div>
						<div class="mb-6">
							<label class="block mb-2 text-sm text-gray-600 dark:text-gray-400">Password</label>
							<input class="w-full px-3 py-2 placeholder-gray-300 border border-gray-300 rounded-md "
								autofocus="autofocus" type="password" placeholder="password를 입력해주세요."
								name="loginPw" maxlength="20" />
						</div>
						<div class="mb-6">
						<button type="sumit" class="w-full px-3 py-4 text-white bg-indigo-500 rounded-md focus:bg-indigo-600 focus:outline-none">
						Sign in
						</button>
						</div>
						<p class="text-sm text-center text-gray-400">
						Don't have an account yet?
						<a href="#" type="sumit" class="text-indigo-400 focus:outline-none focus:underline focus:text-indigo-500 dark:focus:border-indigo-800">
						Sign up
						</a>
						</p>
					</form>
				</div>

			</div>
		</div>
	</section>
</body>
</html>