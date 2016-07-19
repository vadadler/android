=begin

This is oversimplified chat server.

Running on local machine:
To start: ruby teeny_chat_server.rb
To connect using telnet: telnet localhost 4001
Use android client to connect to the sercer.

Participants send messages in JSON format
{"msg":"how are you?", "client_time":1468960139302}

Participants receive messages in the following format:
{"msg":"OK","client_time":1468960328402,"server_time":1468960421527}

=end

require 'socket'
require 'thread'
require 'json'

hostname = 'localhost'
port = 4001

puts "Starting server..."

server = TCPServer.new(hostname, port)
msgs = Queue.new
history = []
participants = []

# Send chat messages to connected participants.
Thread.start {
  while msg = msgs.pop;  # Always true.
    participants.each { |s|
      (s << msg).flush rescue IOError
    }
  end
}

# Receive messages.
loop {
  Thread.start(server.accept) { |sock|
    participants << sock
    begin
      while line = sock.gets;  # Returns nil on EOF.
        puts line
        begin
          m = JSON.parse(line)
        rescue => e
          sock << {error: 'received invalid JSON'}.to_json + "\n"
          sock.flush rescue IOError
        else
          errors = []
          if m["client_time"].nil?
            errors << "client_time is missing"
          end
          if m["msg"].nil?
            errors << "msg required"
          end
        end

        if errors.count == 0
          m["server_time"] = (Time.now.to_f * 1000).to_i
          msgs << "#{m.to_json}\n"
        end
      end
    rescue
      bt = $!.backtrace * "\n  "
      ($stderr << "error: #{$!.inspect}\n  #{bt}\n").flush
    ensure
      participants.delete sock
      sock.close
    end
  }
}
