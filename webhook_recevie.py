from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/webhook', methods=['POST'])
def webhook():
    # 수신된 데이터를 JSON으로 변환합니다.
    data = request.get_json()
    print("수신된 데이터:", data)

    # 수신 확인 메시지를 클라이언트로 보냅니다.
    return jsonify(success=True, message="데이터 수신 완료")

if __name__ == '__main__':
    app.run(debug=True, port=5000)