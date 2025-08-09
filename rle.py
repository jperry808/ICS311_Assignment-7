import re

def rle_compress(message):
    """Compress message w RLE."""
    if not message:
        return ""
    
    compressed = []
    count = 1
    
    for i in range(1, len(message)):
        if message[i] == message[i - 1]:
            count += 1
        else:
            compressed.append(f"{count}{message[i - 1]}")
            count = 1
    compressed.append(f"{count}{message[-1]}")
    
    return ''.join(compressed)


def rle_decompress(encoded_message):
    """Decompress RLE"""
    pattern = r"(\d+)(\D)"
    return ''.join(int(count) * char for count, char in re.findall(pattern, encoded_message))



def send_rle_message(sender, receiver, message_body):
    """Make compressed message with metadata."""
    compressed_body = rle_compress(message_body)
    
    return {
        "sender": sender,
        "receiver": receiver,
        "metadata": {
            "compression": "RLE",
            "original_length": len(message_body)
        },
        "body": compressed_body
    }


def receive_message(message):
    """Decode message based on metadata consistently."""
    if message["metadata"]["compression"] == "RLE":
        decompressed = rle_decompress(message["body"])
        
                if len(decompressed) != message["metadata"]["original_length"]:
            raise ValueError("Message corruption detected: length mismatch")
        
        return decompressed
    

    return message["body"]


# ----------------------------
# Example 
# ----------------------------

msg = send_rle_message("Katelyn", "John", "AAABBBCCC")
print("Sent Message:", msg)

original = receive_message(msg)
print("Decompressed Message:", original)

Sent Message: {
    'sender': 'Katelyn',
    'receiver': 'John',
    'metadata': {'compression': 'RLE', 'original_length': 9},
    'body': '3A3B3C'
}
Decompressed Message: AAABBBCCC
