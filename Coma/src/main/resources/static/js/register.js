document.getElementById('registerForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const id = document.getElementById('id').value;
    const name = document.getElementById('name').value;
    const phoneNumber = document.getElementById('phoneNumber').value;
    const password = document.getElementById('password').value;
    const passwordConfirm = document.getElementById('passwordConfirm').value;

    if (password !== passwordConfirm) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
    }

    const response = await fetch('/api/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: id,
            name: name,
            phoneNumber: phoneNumber,
            password: password
        })
    });

    const result = await response.text();  // 서버로부터의 응답을 텍스트로 가져옴

    if (response.ok) {
        alert("회원가입이 완료되었습니다.");
        window.location.href = window.location.origin + '/users/login'; // 절대 경로로 리다이렉트
    } else {
        alert(result);  // 서버로부터의 에러 메시지를 경고창에 표시
    }
});

async function checkDuplicateId() {
    const id = document.getElementById('id').value;
    const response = await fetch(`/api/users/check-id/${id}`);
    const isDuplicate = await response.json();

    if (isDuplicate) {
        alert("이미 사용 중인 아이디입니다.");
    } else {
        alert("사용 가능한 아이디입니다.");
    }
}
