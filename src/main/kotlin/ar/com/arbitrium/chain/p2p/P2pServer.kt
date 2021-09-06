package ar.com.arbitrium.chain.p2p

import ar.com.arbitrium.chain.model.BlockChain
import java.net.http.WebSocket



/**
 * const wSocket = require('ws');

const P2P_PORT = process.env.P2P_PORT || 5001;
const PEERS = process.env.PEERS ? process.env.PEERS.split(',') : [];
const MESSAGE_TYPES = {chain: 'CHAIN', transaction:'TRANSACTION', clear_txs: 'CLEAR_TRANSACTION'}

class P2pServer {
constructor(blockchain, pool){
this.blockchain = blockchain;
this.pool = pool;
this.sockets = [];
}

esta funcion listen deberiamos llamarla cuando levanta la app (capaz es un evento?)

listen(){
const server = new wSocket.Server({port: P2P_PORT});
server.on('connection', socket => this.connectSocket(socket));
this.connectToPeers();
console.log(`Listening for p2p connections on: ${P2P_PORT}`)
}

connectSocket(socket){
this.sockets.push(socket);
console.log(`New Socket conected!`);
this.messageHandler(socket);
this.sendChain(socket)
}

connectToPeers(){
PEERS.forEach(p => {
const socket = new wSocket(p);
socket.on('open', () => this.connectSocket(socket))
})
}

messageHandler(socket){
socket.on('message', message => {
const {type, chain, transaction} = JSON.parse(message);
switch(type){
case MESSAGE_TYPES.chain:
this.blockchain.replaceChain(chain);
break;
case MESSAGE_TYPES.transaction:
this.pool.updateOrAddTransaction(transaction);
break;
case MESSAGE_TYPES.clear_txs:
this.pool.clear();
break;
}
});
}

syncChains(){
this.sockets.forEach(s => {
this.sendChain(s)
});
}

sendChain(socket){
socket.send(JSON.stringify({
type: MESSAGE_TYPES.chain,
chain: this.blockchain.chain
}));
}

broadcastTx(tx){
this.sockets.forEach(s => this.sendTx(s, tx));
}

sendTx(socket, transaction){
socket.send(JSON.stringify({type: MESSAGE_TYPES.transaction, transaction}));
}

broadcastClear(){
this.sockets.forEach(s => s.send(JSON.stringify({
type: MESSAGE_TYPES.clear_txs
})));
}
}

 */


class P2pServer(var bc: BlockChain, var sockets: MutableList<Unit>) {

}