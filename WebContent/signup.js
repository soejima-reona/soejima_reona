	$("#submit").click(function(){

            // ここが呼び出される
        	let feedback = "";

        	//呼び出し
        	const login_id = document.getElementById('login_id').value;
        	const password = document.getElementById('password').value;
        	const check = document.getElementById('check').value;
        	const name = document.getElementById('name').value;

        	//フォーマット指定
        	const login_idPattern = /^[a-zA-Z0-9]{6,20}$/;
        	const passwordPattern = /^[a-zA-Z0-9!-/:-@¥[-`{-~]{6,20}$/;


        	if (login_id == "") {
        		 feedback += '<li>ログインIDを入力してください</li>';
        	} else if(!login_idPattern.test(login_id)) {
        	     feedback += '<li>ログインIDは6～20文字の半角英数字で入力してください</li>';
        	}

        	if (password == "") {
        		feedback += '<li>パスワードを入力してください</li>';
        	} else if(!passwordPattern.test(password)) {
       	     	feedback += '<li>パスワードは6～20文字の記号を含む半角英数字で入力してください</li>';
        	}

        	if (check == "") {
        		feedback += '<li>確認用パスワードを入力してください</li>';
        	} else if (password != check) {
        		feedback += '<li>パスワードが一致しません</li>';
        	}

        	if (name == "") {
    			feedback += '<li>名称を入力してください</li>';
        	} else if(name.length > 10) {
        		feedback += '<li>名称は10文字以下で入力してください</li>';
        	}

        	if(feedback) {
        		document.querySelector('.feedback').innerHTML = feedback;
        		return false;
        	}else {
        		return true;
        	}
    });