local n = tonumber(ARGV[1])
if not n  or n == 0 then
    return 0
end
local stock = tonumber(redis.call("GET", KEYS[1]));
if not stock then
    return 0
end
if stock - n >= 0 then
    redis.call("DECRBY", KEYS[1], n)
    return n;
end
return 0