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

    const verificationCode = document.getElementById('verificationCode').value;
    if (!verificationCode || !isVerified) {  // isVerified는 인증 완료 여부를 나타내는 변수
        alert("전화번호 인증을 완료해주세요.");
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
        window.location.href = window.location.origin + '/users/login';
    } else {
        alert(result);
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

let isVerified = false;  // 인증 완료 여부를 저장하는 변수

async function sendVerificationCode() {
    const phoneNumber = document.getElementById('phoneNumber').value;

    if (!phoneNumber.match(/^010\d{8}$/)) {
        alert("전화번호는 숫자 형태로만 입력해야 합니다.");
        return;
    }

    const response = await fetch(`/api/sms/send`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ phoneNumber: phoneNumber })
    });

    if (response.ok) {
        alert("인증번호가 전송되었습니다.");
        document.getElementById('verificationSection').style.display = 'block'; // 인증번호 입력 필드 보이기
    } else {
        alert("인증번호 전송에 실패했습니다.");
    }
}

async function verifyCode() {
    const phoneNumber = document.getElementById('phoneNumber').value;
    const verificationCode = document.getElementById('verificationCode').value;
    const verifyButton = document.querySelector('button[onclick="verifyCode()"]'); // 인증 확인 버튼
    const sendButton = document.querySelector('button[onclick="sendVerificationCode()"]'); // 인증 버튼

    const response = await fetch(`/api/sms/verify`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            phoneNumber: phoneNumber,
            code: verificationCode
        })
    });

    if (response.ok) {
        alert("인증이 완료되었습니다.");
        isVerified = true;
        document.getElementById('verificationSection').style.display = 'none'; // 인증번호 입력 필드 숨기기

        // 인증 버튼 비활성화
        sendButton.disabled = true;
        sendButton.style.backgroundColor = 'gray';
        sendButton.style.cursor = 'not-allowed';
    } else {
        alert("인증에 실패했습니다. 인증번호를 다시 확인해주세요.");
        isVerified = false;
    }
}

