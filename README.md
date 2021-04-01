Goal:

- Find the fastest function that handles rate limiting policies for page requests using a hashing function
  where page requests get sent and returned along with storing page counts.
  
Uses: A Que & Hash Function
  - When a page gets requested, it might be in memory so if it's stored I need that node to detach itself and place itself at the start
    of the list.
    Node is in memory and gets referenced -> Node detaches and heads to the front of the bus.
    Node isn't in memory -> A new node needs to be added and sent to the front of the bus but needs to also update
    it's new address inside the hash storage.
    
Que: If the que or (frame) is full ->
- Eject the last node from the 
backseat of the bus and add a new node to the front of the bus to replace it. 
- This function needs to be able to have the ability store values for me as well in cases where the page isn't stored 
  already, and it will also need to bring that page into memory once referenced.
- (LRU): Evict the mapped items by lowest rank: lowest rank item will be removed when a new one inserted and there’s
no space left in the cache. Every time an item is inserted into the cache it’s rank is set to the highest
rank.
- (MRU): Most Recent: evict the item in the map w/ the highest rank: Highest rank item will be removed when a new
one is inserted and there’s no space left in the cache. Another note is that every time an item is inserted
into the cache it’s rank is set to the highest rank.
 
-  Edge Cases. One issue to address is the cost of finding which key needs to be evicted once capacity is
    reached.
 - I've chosen to use an LRU cache to provide an implementation that will allow for me to do this
    w/out incurring additional cost without losing the speed.
 - I chose a linked list here to reduce unnecessary overhead from write operations. Using linked list makes it
    inexpensive to remove items from request headers so the LRU. 
 - LRU caching doesn't hold the performance impact for insertions into the caches once their capacity has been reached, and I wanted to optimize writes for this experiment so that is the reasons I've decided to implement it.
  - The Performance is O(1) and for cache eviction, it is still O(1).
-----------------------------------------------------------------------------------------------------------------------
- Speed:
My write and remove is O(1).
The hashmap (get) is O(1).
The header of the insertions origin also gets removed which is O(1) still.