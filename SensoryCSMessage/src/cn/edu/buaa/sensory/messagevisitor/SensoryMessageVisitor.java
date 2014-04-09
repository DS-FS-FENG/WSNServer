package cn.edu.buaa.sensory.messagevisitor;

import java.io.DataInputStream;
import java.io.IOException;

import cn.edu.buaa.sensory.message.IProtocolMessage;
import cn.edu.buaa.sensory.message.MessageException;
import cn.edu.buaa.sensory.message.request.AuthenticationRequest;
import cn.edu.buaa.sensory.message.request.DeleteAllHistoryRequest;
import cn.edu.buaa.sensory.message.request.DeleteNodeHistoryRequest;
import cn.edu.buaa.sensory.message.request.ReadActivedNodesRequest;
import cn.edu.buaa.sensory.message.request.ReadLastInformationRequest;
import cn.edu.buaa.sensory.message.request.ReadNodeHistoryRequest;
import cn.edu.buaa.sensory.message.request.ReadNodesHistoryErrorRequest;
import cn.edu.buaa.sensory.message.response.AuthenticationResponse;
import cn.edu.buaa.sensory.message.response.DeleteHistoryResponse;
import cn.edu.buaa.sensory.message.response.ExceptionResponse;
import cn.edu.buaa.sensory.message.response.ReadActivedNodesResponse;
import cn.edu.buaa.sensory.message.response.ReadLastInformationResponse;
import cn.edu.buaa.sensory.message.response.ReadNodeHistoryResponse;
import cn.edu.buaa.sensory.message.response.ReadNodesHistoryErrorResponse;

class SensoryMessageVisitor extends MessageVisitor {

	public SensoryMessageVisitor(IMessageWriter writer) {
		super(writer);
	}
	

	@Override
	public IProtocolMessage readRequest(DataInputStream iStream) throws MessageException {
		try {
			byte protocolhead = iStream.readByte();
			if(protocolhead != IProtocolMessage.ProtocolHead)
				throw new MessageException(IProtocolMessage.UnexpectSreamSource);
			
			byte requestType = iStream.readByte();
			IProtocolMessage msg;
			switch (requestType) {
				case IProtocolMessage.ReadLastInformation:
					msg = new ReadLastInformationRequest(); msg.read(iStream); break;
				case IProtocolMessage.ReadNodeHistoryData:
					msg = new ReadNodeHistoryRequest(); msg.read(iStream); break;
				case IProtocolMessage.ReadNodesHistoryError:
					msg = new ReadNodesHistoryErrorRequest(); msg.read(iStream); break;
				case IProtocolMessage.ReadActivedNodes:
					msg = new ReadActivedNodesRequest(); msg.read(iStream); break;
				case IProtocolMessage.DeleteNodeHistoryData:
					msg = new DeleteNodeHistoryRequest(); msg.read(iStream); break;
				case IProtocolMessage.DeleteAllHistoryData:
					msg = new DeleteAllHistoryRequest(); msg.read(iStream); break;
				case IProtocolMessage.Authentication:
					msg = new AuthenticationRequest(); msg.read(iStream); break;
				default:
					throw new MessageException(IProtocolMessage.UnsuppotedFunction);
			}
			return msg;
		} catch (IOException e) {
			throw new MessageException(IProtocolMessage.IOFailure);
		}
	}

	@Override
	public IProtocolMessage readResponse(DataInputStream iStream) throws MessageException {
		try {
			byte protocolhead = iStream.readByte();
			if(protocolhead != IProtocolMessage.ProtocolHead)
				return new ExceptionResponse(IProtocolMessage.UnexpectSreamSource);
			
			byte responseType = iStream.readByte();
			IProtocolMessage msg;
			switch (responseType) {
				case IProtocolMessage.ReadLastInformation:
					msg = new ReadLastInformationResponse(); msg.read(iStream); break;
				case IProtocolMessage.ReadNodeHistoryData:
					msg = new ReadNodeHistoryResponse(); msg.read(iStream); break;
				case IProtocolMessage.ReadNodesHistoryError:
					msg = new ReadNodesHistoryErrorResponse(); msg.read(iStream); break;
				case IProtocolMessage.ReadActivedNodes:
					msg = new ReadActivedNodesResponse(); msg.read(iStream); break;
				case IProtocolMessage.DeleteNodeHistoryData:
				case IProtocolMessage.DeleteAllHistoryData:
					msg = new DeleteHistoryResponse(responseType); msg.read(iStream); break;
				case IProtocolMessage.Authentication:
					msg = new AuthenticationResponse(); msg.read(iStream); break;
				default:
					throw new MessageException(IProtocolMessage.UnsuppotedFunction);
			}
			return msg;
		} catch (IOException e) {
			throw new MessageException(IProtocolMessage.IOFailure);
		}
	}

}
