Goal:

What am I trying to do here ? Find the fastest implementation of rate limiting policies for requests to a in memory
DB where it will handle numerous write operations.

-----------------------------------------------------------------------------------------------------------------------
(LRU): Evict the mapped items by lowest rank: lowest rank item will be removed when a new one inserted and there’s
no space left in the cache. Every time an item is inserted into the cache it’s rank is set to the highest
rank.
(MRU): Most Recent: evict the item in the map w/ the highest rank: Highest rank item will be removed when a new
one is inserted and there’s no space left in the cache. Another note is that every time an item is inserted
into the cache it’s rank is set to the highest rank.
 ----------------------------------------------------------------------------------------------------------------------
LRU vs MRU: Edge Cases and Why I chose an LRU for this implementation.
One issue to address is the cost of finding which key needs to be evicted once capacity is
reached.
I've chosen to use a LRU cache to provide an implementation that will allow for me to do this
w/out incurring additional cost without losing the speed.
I chose a linked list here to reduce unnecessary overhead from write operations. Using linked list makes it
inexpensive to remove items from request headers so the LRU.
LRU caching doesnt hold the performance impact for insertions into the caches once their capacity has been reached
and I wanted to optimize writes for this experiment so that is the reasons I've decided to implement it.
The Performance is O(1) and for cache eviction, it is still O(1).
-----------------------------------------------------------------------------------------------------------------------
Speed:
My write and remove is O(1).
The hashmap (get) is O(1).
The header of the insertions origin also gets removed which is O(1) still.