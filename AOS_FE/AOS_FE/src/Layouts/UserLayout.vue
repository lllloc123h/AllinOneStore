<template>
  <div>
    <UserHeader></UserHeader>
    <main>
      <router-view></router-view>
     <df-messenger
        location="us-central1"
        project-id="optimum-monitor-461705-m7"
        agent-id="f98675fe-62c3-4b43-9ac2-afa6a57af0a0"
        language-code="vi"
        max-query-length="-1"
      >
        <df-messenger-chat-bubble chat-title="Chat bot cực vip">
        </df-messenger-chat-bubble>
      </df-messenger>
    </main>
    <UserFooter></UserFooter>
  </div>
</template>
<script setup>
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";
import UserHeader from "../components/Module/UserHeader.vue";
import UserFooter from "../components/Module/UserFooter.vue";
</script>
<style>
df-messenger {
  z-index: 999;
  position: fixed;
  --df-messenger-font-color: #000;
  --df-messenger-font-family: Google Sans;
  --df-messenger-chat-background: #f3f6fc;
  --df-messenger-message-user-background: #d3e3fd;
  --df-messenger-message-bot-background: #fff;
  bottom: 16px;
  right: 16px;
}
</style>
