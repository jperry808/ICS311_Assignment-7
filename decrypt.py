import hashlib # provides hash functions, SHA-256

# convert into bytes then hash them then convert to integers
def hashMessage(message):
    messageBytes = message.encode('utf-8') #turn string into bytes
    hashBytes = hashlib.sha256(messageBytes).digest() # compute SHA-256 hash
    return int.from_bytes(hashBytes, byteorder='big') # convert hash into big integer
    
# decrypt signature using sender's public key (n, e)
def decrypt(signature, e, n):
    return pow(signature, e, n) # C^e mod n
    
# verify signature, rehash original message ourselves
def verify(message, signature, e, n):
    messageHash = hashMessage(message)
    decryptedHash = decrypt(signature, e, n) # decrypt signature to get orignal hashMessage
    if messageHash == decryptedHash:
        return True # authentic
    else:
        return False # not authentic

# test cases, I used random numbers since problem 3 code was not done yet
publicKeyN = 41541548417896984719696796324813
publicKeyE = 978162364857
signature = 54687953687541584791486977896
message = "Hello World!"

if verify(message, signature, publicKeyE, publicKeyN): # check if the two hashes match
    print("Message is authentic and untampered.")
else:
    print("Message is NOT authentic.")