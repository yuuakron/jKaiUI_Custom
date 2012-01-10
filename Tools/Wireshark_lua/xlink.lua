-- works as of Wireshark Version 1.0.5
do
    -- (1) Dissector の情報を格納するテーブルを作成します。
    --     この情報をあとから Wireshark に登録します。
    -- 第1引数で指定している文字列は、この Dissector の名前です。
    -- フィルタの選択画面等で表示されます。
    -- 第2引数で指定している文字列は、この Dissector の説明です。
    xlink_proto = Proto("XLINK", "XLINK protocol dissector")

    -- (2) Dissector の実際の処理をする関数を (1) で作ったテーブルに設定します。
    -- 引数の意味はそれぞれ以下の通り：
    --   buffer: パケットのデータが入った tvb
    --   pinfo:  パケットの概要を GUI のリスト部分に表示するために使うオブジェクト
    --   tree:   パケットを解析した情報をツリー形式で GUI のツリー部分に表示
    --           するために使うオブジェクト
    xlink_proto.dissector = function(buffer, pinfo, tree)
        local code_range = buffer(0,2)
        local code = code_range:uint()

        local code_names = { 
			[0x213b] = "XLINK:!:? data receive",
			[0x233b] = "XLINK:#:? data receive",
			[0x263b] = "XLINK:&:avatar url receive",
			[0x2b3b] = "XLINK:+:arena or root info update",
			[0x303b] = "XLINK:0:ip and port exchange",
            [0x313b] = "XLINK:1:friend location info request", 
            [0x323b] = "XLINK:2:friend location info reply", 
            [0x333b] = "XLINK:3:Ping request", 
            [0x343b] = "XLINK:4:Ping reply", 
			[0x353b] = "XLINK:5:nothing", 
			[0x363b] = "XLINK:6:user info?(client disconnect)", 
			[0x373b] = "XLINK:7:loggined friend info", 
			[0x383b] = "XLINK:8:nothing", 
			[0x393b] = "XLINK:9:closed room info", 
			[0x403b] = "XLINK:@:?",
            [0x413b] = "XLINK:A:chat receive",
            [0x423b] = "XLINK:B:user in",
            [0x433b] = "XLINK:C:user out2",
            [0x443b] = "XLINK:D:room info",
            [0x453b] = "XLINK:E:user info",
            [0x463b] = "XLINK:F:user out1",
            [0x473b] = "XLINK:G:arena info",
            [0x483b] = "XLINK:H:nothing",
            [0x493b] = "XLINK:I:nothing",
            [0x4a3b] = "XLINK:J:nothing",
            [0x4b3b] = "XLINK:K:live check?",
            [0x503b] = "XLINK:P:PM",
			[0x6c3b] = "XLINK:l:room allow?",
			[0x703b] = "XLINK:p:DATA(client disconnect)",
            [0x713b] = "XLINK:q:DATA",
			[0x723b] = "XLINK:r:server info",
			[0x7e3b] = "XLINK:~:registered friend list",
            [0x4b41] = "XLINK:KA:KAI_",
        }
        
        local subtree = tree:add("XLINK Protocol Data")
        subtree:add(code_range, "Code:", code, code_names[code])

		if code == 0x333b then

		elseif code == 0x4b3b then

		else

        	dispatch(code, version, buffer(2, buffer:len()-2):tvb(), pinfo, subtree)
        
		end

        pinfo.cols.protocol = "XLINK"
        pinfo.cols.info = code_names[code]
    end

    -- (3) Dissector を Wireshark に登録します。
    udp_table = DissectorTable.get("udp.port")
	tcp_table = DissectorTable.get("tcp.port")
--    for i, port in pairs( { 10000, 10001 } ) do 
        udp_table:add(30000, xlink_proto)
		udp_table:add(34525, xlink_proto)
		tcp_table:add(34525, xlink_proto)
--    end

end


function dispatch(code, ver, buffer, pinfo, tree)
    local subtree = tree:add(buffer(0), "Value:", buffer(0):tvb())

--	local dest = buffer(0,6):ether()
--	local source = buffer(6,6):ether()

	if code == 0x713b then
		subtree:add(buffer(0, 6), "Destination:", string.format("%x:%x:%x:%x:%x:%x",buffer(0,1):uint(),buffer(1,1):uint(),buffer(2,1):uint(),buffer(3,1):uint(),buffer(4,1):uint(),buffer(5,1):uint()))
		subtree:add(buffer(6, 6), "Source:", string.format("%x:%x:%x:%x:%x:%x",buffer(6,1):uint(),buffer(7,1):uint(),buffer(8,1):uint(),buffer(9,1):uint(),buffer(10,1):uint(),buffer(11,1):uint()))
		subtree:add(buffer(12,2), "Type:", string.format("0x%x",buffer(12,2):uint()))
		subtree:add(buffer(14,buffer:len()-6-6-2), "DATA:", buffer(14,buffer:len()-6-6-2):string())

	elseif code == 0x703b then
		
		subtree:add(buffer(0, 6), "Destination:", string.format("%x:%x:%x:%x:%x:%x",buffer(0,1):uint(),buffer(1,1):uint(),buffer(2,1):uint(),buffer(3,1):uint(),buffer(4,1):uint(),buffer(5,1):uint()))
		subtree:add(buffer(6, 6), "Source:", string.format("%x:%x:%x:%x:%x:%x",buffer(6,1):uint(),buffer(7,1):uint(),buffer(8,1):uint(),buffer(9,1):uint(),buffer(10,1):uint(),buffer(11,1):uint()))
		subtree:add(buffer(12,2), "Type:", string.format("0x%x",buffer(12,2):uint()))
		subtree:add(buffer(14,buffer:len()-6-6-2), "DATA:", buffer(14,buffer:len()-6-6-2):string())

	else

		subtree:add(buffer(0,buffer:len()), "DATA:", buffer(0,buffer:len()):string())

	end

--    if code == 0x01 then
--        subtree:add(buffer(0,7), "Postal Code:", buffer(0,7):string())
--    elseif code == 0x02 then
--        subtree:add(buffer(0,1), "Converted:", buffer(0,1):uint())
--        subtree:add(buffer(1,7), "Postal Code:", buffer(1,7):string())
--        subtree:add(buffer(8), "Address:", buffer(8):string())
--    elseif code == 0x11 then
--        subtree:add(buffer(0), "Address:", buffer(0):string())
--    elseif code == 0x12 then
--        subtree:add(buffer(0,1), "Converted:", buffer(0,1):uint())
--        subtree:add(buffer(1,7), "Postal Code:", buffer(1,7):string())
--        subtree:add(buffer(8), "Address:", buffer(8):string())
--    end
end