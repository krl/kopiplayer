# kopiplayer

This is a html5 music player with musicbrainz coupling. The idea is to index your files from the perspective of musicbrainz artists, releases and recordings. The idea is to make the tags in the actual files obsolete and just provide a recording-id -> file mapping.

The player has a html5 web frontend and communicates with the backend part over a websocket and the audio tag..

## Status

Still alpha software, the indexing works with a few test albums, but needs more heuristics. No playlist support yet, but you can listen to any song you click on.

## License

Copyright © 2013 Kristoffer Ström

Distributed under the Eclipse Public License, the same as Clojure.
