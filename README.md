Goal:
-------------------------------------------------------------------------------------------------------
- Find the fastest function that handles rate limiting policies for page requests using a hashing function
  where page requests get sent and returned along with storing page counts.
  
Uses: A Que & Hash Function:
--------------------------------------------------------------------------------------------------------
- When a page gets requested, it may or may not be in memory.
- If Node is in memory and gets referenced: 
  - I need that node to detach itself and place itself at the start of my list.
- Node isn't in memory:
  - A new node needs to be added and sent to the front of the bus but needs to also update it's new address inside the hash storage.
    
Que: If the que or (frame) is full:
- Eject the last node from the back of the bus and add a new one to the front of the bus to replace it. 
- This function also needs to have the ability to store values when the page isn't found. 
- It will need to bring that new page being requested into memory once referenced.
- (LRU): Evict the mapped items by lowest rank: lowest rank item will be removed when a new one inserted and there’s
no space left in the cache. Every time an item is inserted into the cache it’s rank is set to the highest
rank.
- (MRU): Most Recent: evict the item in the map w/ the highest rank: Highest rank item will be removed when a new
one is inserted and there’s no space left in the cache. Another note is that every time an item is inserted
into the cache it’s rank is set to the highest rank.
 
- Edge Cases. 
- One issue to address is the cost of finding which key needs to be evicted once capacity is reached. 
- The LRU cache will provide an implementation that allows for me to do this w/out incurring additional cost without losing the speed.
- I chose a linked list since I'm using a Que. Using linked list makes it inexpensive to remove items from request headers so the LRU. 
- LRU caching doesn't hold the performance impact for insertions into the caches once their capacity has been reached, and I wanted to optimize writes for this experiment so that is the reasons I've decided to implement it.
- The Performance is O(1) and for cache eviction, it is still O(1).
-----------------------------------------------------------------------------------------------------------------------
- Speed:
- My write and remove is O(1).
- The hashmap (get) is O(1).
- The header of the insertions' origin also gets removed which is O(1) still.