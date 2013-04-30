goog.provide('kopiplayer');
goog.require('cljs.core');
goog.require('cljs.reader');
goog.require('jayq.core');
goog.require('cljs.reader');
goog.require('jayq.core');
goog.require('crate.core');
kopiplayer.$body = jayq.core.$.call(null,"\uFDD0'body");
kopiplayer.$audio = jayq.core.$.call(null,"\uFDD0'#audio");
kopiplayer.$artists = jayq.core.$.call(null,"\uFDD0'#artists");
kopiplayer.$artist_info = jayq.core.$.call(null,"\uFDD0'#artist_info");
kopiplayer.websocket = (new WebSocket("ws://localhost:8080/socket"));
/**
* @param {...*} var_args
*/
kopiplayer.message = (function() { 
var message__delegate = function (stuff){
console.log([cljs.core.str("message "),cljs.core.str(stuff)].join(''));
return kopiplayer.websocket.send(cljs.core.js__GT_clj.call(null,cljs.core.vec.call(null,stuff)));
};
var message = function (var_args){
var stuff = null;
if (goog.isDef(var_args)) {
  stuff = cljs.core.array_seq(Array.prototype.slice.call(arguments, 0),0);
} 
return message__delegate.call(this, stuff);
};
message.cljs$lang$maxFixedArity = 0;
message.cljs$lang$applyTo = (function (arglist__11447){
var stuff = cljs.core.seq(arglist__11447);;
return message__delegate(stuff);
});
message.cljs$lang$arity$variadic = message__delegate;
return message;
})()
;
var group__3384__auto___11448 = cljs.core.swap_BANG_.call(null,crate.core.group_id,cljs.core.inc);
kopiplayer.letter_box_tpl = (function letter_box_tpl(letter){
var elem__3385__auto__ = crate.core.html.call(null,cljs.core.PersistentVector.fromArray(["\uFDD0'div.letterbox",cljs.core.PersistentVector.fromArray(["\uFDD0'div.letter_separator",cljs.core.PersistentVector.fromArray(["\uFDD0'h1.letter",letter], true)], true)], true));
elem__3385__auto__.setAttribute("crateGroup",group__3384__auto___11448);
return elem__3385__auto__;
});
kopiplayer.letter_box_tpl.prototype._crateGroup = group__3384__auto___11448;
var group__3384__auto___11449 = cljs.core.swap_BANG_.call(null,crate.core.group_id,cljs.core.inc);
kopiplayer.artist_entry_tpl = (function artist_entry_tpl(artist){
var elem__3385__auto__ = crate.core.html.call(null,cljs.core.PersistentVector.fromArray(["\uFDD0'h2",cljs.core.ObjMap.fromObject(["\uFDD0'data-value"],{"\uFDD0'data-value":artist}),(new cljs.core.Keyword("\uFDD0'name")).call(null,artist)], true));
elem__3385__auto__.setAttribute("crateGroup",group__3384__auto___11449);
return elem__3385__auto__;
});
kopiplayer.artist_entry_tpl.prototype._crateGroup = group__3384__auto___11449;
var group__3384__auto___11450 = cljs.core.swap_BANG_.call(null,crate.core.group_id,cljs.core.inc);
kopiplayer.recording_tpl = (function recording_tpl(recording){
var elem__3385__auto__ = crate.core.html.call(null,cljs.core.PersistentVector.fromArray(["\uFDD0'tr",cljs.core.ObjMap.fromObject(["\uFDD0'data-value"],{"\uFDD0'data-value":recording}),cljs.core.PersistentVector.fromArray(["\uFDD0'td",cljs.core.PersistentVector.fromArray(["\uFDD0'b",(new cljs.core.Keyword("\uFDD0'number")).call(null,recording)], true)], true),cljs.core.PersistentVector.fromArray(["\uFDD0'td",(new cljs.core.Keyword("\uFDD0'title")).call(null,recording)], true),cljs.core.PersistentVector.fromArray(["\uFDD0'td",(new cljs.core.Keyword("\uFDD0'length")).call(null,recording)], true)], true));
elem__3385__auto__.setAttribute("crateGroup",group__3384__auto___11450);
return elem__3385__auto__;
});
kopiplayer.recording_tpl.prototype._crateGroup = group__3384__auto___11450;
jayq.core.delegate.call(null,kopiplayer.$body,kopiplayer.recording_tpl,"\uFDD0'click",(function (e){
e.preventDefault();
var me = this;
var value = cljs.reader.read_string.call(null,jayq.core.data.call(null,jayq.core.$.call(null,me),"\uFDD0'value"));
return kopiplayer.message.call(null,"\uFDD0'play-recording",(new cljs.core.Keyword("\uFDD0'id")).call(null,value));
}));
jayq.core.delegate.call(null,kopiplayer.$body,kopiplayer.artist_entry_tpl,"\uFDD0'click",(function (e){
e.preventDefault();
var me = this;
var value = cljs.reader.read_string.call(null,jayq.core.data.call(null,jayq.core.$.call(null,me),"\uFDD0'value"));
return kopiplayer.message.call(null,"\uFDD0'artist-info",(new cljs.core.Keyword("\uFDD0'id")).call(null,value));
}));
var group__3384__auto___11455 = cljs.core.swap_BANG_.call(null,crate.core.group_id,cljs.core.inc);
kopiplayer.artist_info_tpl = (function artist_info_tpl(artist){
var elem__3385__auto__ = crate.core.html.call(null,cljs.core.PersistentVector.fromArray(["\uFDD0'div",cljs.core.PersistentVector.fromArray(["\uFDD0'h1",(new cljs.core.Keyword("\uFDD0'name")).call(null,artist)], true),(function (){var iter__2540__auto__ = (function iter__11453(s__11454){
return (new cljs.core.LazySeq(null,false,(function (){
var s__11454__$1 = s__11454;
while(true){
if(cljs.core.seq.call(null,s__11454__$1))
{var release = cljs.core.first.call(null,s__11454__$1);
return cljs.core.cons.call(null,cljs.core.PersistentVector.fromArray(["\uFDD0'div",cljs.core.PersistentVector.fromArray(["\uFDD0'h2",(new cljs.core.Keyword("\uFDD0'title")).call(null,release),cljs.core.PersistentVector.fromArray(["\uFDD0'p.date",(new cljs.core.Keyword("\uFDD0'date")).call(null,release)], true)], true),cljs.core.PersistentVector.fromArray(["\uFDD0'table",cljs.core.map.call(null,kopiplayer.recording_tpl,(new cljs.core.Keyword("\uFDD0'recordings")).call(null,release))], true)], true),iter__11453.call(null,cljs.core.rest.call(null,s__11454__$1)));
} else
{return null;
}
break;
}
}),null));
});
return iter__2540__auto__.call(null,(new cljs.core.Keyword("\uFDD0'releases")).call(null,artist));
})()], true));
elem__3385__auto__.setAttribute("crateGroup",group__3384__auto___11455);
return elem__3385__auto__;
});
kopiplayer.artist_info_tpl.prototype._crateGroup = group__3384__auto___11455;
kopiplayer.display_artists = (function display_artists(artists){
var G__11459 = cljs.core.seq.call(null,artists);
while(true){
if(G__11459)
{var vec__11460 = cljs.core.first.call(null,G__11459);
var letter = cljs.core.nth.call(null,vec__11460,0,null);
var artistlist = cljs.core.nth.call(null,vec__11460,1,null);
var box_11462 = jayq.core.$.call(null,kopiplayer.letter_box_tpl.call(null,cljs.core.name.call(null,letter)));
var G__11461_11463 = cljs.core.seq.call(null,artistlist);
while(true){
if(G__11461_11463)
{var artist_11464 = cljs.core.first.call(null,G__11461_11463);
jayq.core.append.call(null,box_11462,kopiplayer.artist_entry_tpl.call(null,artist_11464));
{
var G__11465 = cljs.core.next.call(null,G__11461_11463);
G__11461_11463 = G__11465;
continue;
}
} else
{}
break;
}
jayq.core.append.call(null,kopiplayer.$artists,box_11462);
{
var G__11466 = cljs.core.next.call(null,G__11459);
G__11459 = G__11466;
continue;
}
} else
{return null;
}
break;
}
});
kopiplayer.display_artist_info = (function display_artist_info(artist){
return jayq.core.append.call(null,jayq.core.inner.call(null,kopiplayer.$artist_info,""),kopiplayer.artist_info_tpl.call(null,artist));
});
kopiplayer.play_recording = (function play_recording(id){
jayq.core.attr.call(null,kopiplayer.$audio,"\uFDD0'src",[cljs.core.str("/play/"),cljs.core.str(id)].join(''));
return cljs.core.first.call(null,kopiplayer.$audio).play();
});
kopiplayer.handle_message = (function handle_message(p__11467){
var vec__11470 = p__11467;
var command = cljs.core.nth.call(null,vec__11470,0,null);
var args = cljs.core.nthnext.call(null,vec__11470,1);
console.log(cljs.core.print_str.call(null,"handle-message",command));
var G__11471 = command;
if(cljs.core._EQ_.call(null,"\uFDD0'play",G__11471))
{return kopiplayer.play_recording.call(null,cljs.core.first.call(null,args));
} else
{if(cljs.core._EQ_.call(null,"\uFDD0'artists",G__11471))
{return kopiplayer.display_artists.call(null,cljs.core.first.call(null,args));
} else
{if(cljs.core._EQ_.call(null,"\uFDD0'artist-info",G__11471))
{return kopiplayer.display_artist_info.call(null,cljs.core.first.call(null,args));
} else
{if("\uFDD0'else")
{throw (new Error([cljs.core.str("No matching clause: "),cljs.core.str(command)].join('')));
} else
{return null;
}
}
}
}
});
kopiplayer.websocket.onopen = (function (){
return console.log("connected!");
});
kopiplayer.websocket.onmessage = (function (p1__11472_SHARP_){
return kopiplayer.handle_message.call(null,cljs.reader.read_string.call(null,p1__11472_SHARP_.data));
});
