/**
 * 创建websockt 连接
 * @param url 连接
 * @param message 接受推送的处理方法
 * @param open 连接成功回调方法
 * @param close 连接关闭的回调方法
 * @returns {WebSocket}
 */
export const createWebSocket = (url, message, open, close) => {
  var socket = new WebSocket(url)
  // 2. open: 当和websocket服务连接成功的时候触发
  if (!!open) {
    socket.addEventListener('open', open)
  }
  // 4. 接收websocket服务的数据
  socket.addEventListener('message', message)
  if (!!close) {
    socket.addEventListener('close', close)
  }
  socket.addEventListener("error", function (event) {
    // handle error event
    console.log("error", event);
  })
  return socket;
}

