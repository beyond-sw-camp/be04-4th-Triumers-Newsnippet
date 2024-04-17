<template>
    <footer class="footer">
        <div class="footer-contact">
          <div class="footer-contact-item">Tel. 000-000-0000</div>
          <div class="footer-contact-item">Fax. 000-000-0000</div>
          <div class="footer-contact-item">Email. abc@gmail.com</div>
          <div class="footer-contact-item">©Newsnippet</div>
        </div>
        <div class="footer-content">
        <div class="footer-links">
          <router-link to="/" class="footer-link">메인 화면</router-link>
          <router-link to="/policy-about" class="footer-link">서비스 소개</router-link>
          <router-link to="/policy-service" class="footer-link">이용약관</router-link>
          <router-link to="/policy-privacy" class="footer-link">개인정보 처리방침</router-link>
        </div>
      </div>
    </footer>
  </template>
  
  <style scoped>
  .footer{

    font-family: 'ONE-Mobile-Title';
    align-self: stretch;
    margin-top: 300px;
    width: auto;
    letter-spacing: -0.16px;
    line-height: 150%;
    justify-content: center;
    padding: 36px 60px;

    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #393B63;
    color: #ffffff;
    flex-wrap: wrap; 

  }

  @font-face {
  font-family: 'ONE-Mobile-Title';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2105_2@1.0/ONE-Mobile-Title.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
  
  .footer-contact,
  .footer-links {
    display: flex;
    gap: 10px; /* 각 항목 사이의 간격을 조정 */
  }

  .footer-links *{
    color: #ffffff;
  }
  
  /* 항목들 사이에 구분자 추가 */
  .footer-contact-item:not(:last-child)::after,
  .footer-link:not(:last-child)::after {
    content: '|';
    padding-left: 10px;
  }
  
  @media (max-width: 991px) {
    .footer {
      flex-direction: column;
      align-items: flex-start;
    }
  
    .footer-contact-item::after,
    .footer-link::after {
      content: none;
    }
  }
  </style>