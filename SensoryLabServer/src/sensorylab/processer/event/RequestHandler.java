package sensorylab.processer.event;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import cn.edu.buaa.sensory.message.IProtocolMessage;
import cn.edu.buaa.sensory.message.Permission;
import cn.edu.buaa.sensory.message.request.*;
import cn.edu.buaa.sensory.message.response.AuthenticationResponse;
import cn.edu.buaa.sensory.message.response.DeleteHistoryResponse;
import cn.edu.buaa.sensory.message.response.ExceptionResponse;
import cn.edu.buaa.sensory.message.response.ReadActivedNodesResponse;
import cn.edu.buaa.sensory.message.response.ReadLastInformationResponse;
import cn.edu.buaa.sensory.message.response.ReadNodeHistoryResponse;
import cn.edu.buaa.sensory.message.response.ReadNodesHistoryErrorResponse;
import framework.processer.data.IData;
import sensorylab.processer.SensoryDataBaseProcessor;
import sensorylab.processer.SensoryException;
import sensorylab.processer.data.ClientData;
import sensorylab.processer.data.Data;
import sensorylab.processer.data.UserInfoData;
import utilities.Utilities;

public class RequestHandler {
	
	public static ClientData handle(IData request, Connection conn, SensoryDataBaseProcessor dbProcessor) {
		IProtocolMessage msg = ((ClientData)request).getProtocolMessage();
		ClientData response;
		switch (msg.getDataType()) {
		case IProtocolMessage.ReadLastInformation: {
			ReadLastInformationRequest req = (ReadLastInformationRequest)msg;
			response = new ClientData(handle(req, conn, dbProcessor));
			break;
		}
		case IProtocolMessage.ReadNodeHistoryData: {
			ReadNodeHistoryRequest req = (ReadNodeHistoryRequest)msg;
			response = new ClientData(handle(req, conn, dbProcessor));
			break;
		}
		case IProtocolMessage.ReadNodesHistoryError: {
			ReadNodesHistoryErrorRequest req = (ReadNodesHistoryErrorRequest)msg;
			response = new ClientData(handle(req, conn, dbProcessor));
			break;
		}
		case IProtocolMessage.ReadActivedNodes: {
			ReadActivedNodesRequest req = (ReadActivedNodesRequest)msg;
			response = new ClientData(handle(req, conn, dbProcessor));
			break;
		}
		case IProtocolMessage.DeleteNodeHistoryData: {
			DeleteNodeHistoryRequest req = (DeleteNodeHistoryRequest)msg;
			response = new ClientData(handle(req, conn, dbProcessor));
			break;
		}
		case IProtocolMessage.DeleteAllHistoryData: {
			DeleteAllHistoryRequest req = (DeleteAllHistoryRequest)msg;
			response = new ClientData(handle(req, conn, dbProcessor));
			break;
		}
		case IProtocolMessage.Authentication: {
			AuthenticationRequest req = (AuthenticationRequest)msg;
			response = new ClientData(handle(req, conn, dbProcessor));
			break;
		}
		default:
			response = new ClientData(new ExceptionResponse(IProtocolMessage.UnsuppotedFunction));
			break;
		}
		
		return response;
	}
	
	private static IProtocolMessage handle(AuthenticationRequest request, Connection conn, SensoryDataBaseProcessor dbProcessor) {
		IProtocolMessage response;
		switch (request.getOperationType()) {
		case AuthenticationRequest.LogIn:
			response = userLogIn(request, conn, dbProcessor);
			break;
		case AuthenticationRequest.Register:
			response = userRegister(request, conn, dbProcessor);
			break;
		case AuthenticationRequest.LogOff:
			response = userLogOff(request, conn, dbProcessor);
			break;
		default:
			response = new ExceptionResponse(IProtocolMessage.UnsuppotedFunction);
			break;
		}
		return response;
	}
	
	private static IProtocolMessage userLogIn(AuthenticationRequest request, Connection conn, SensoryDataBaseProcessor dbProcessor) {
		try {
			UserInfoData data = (UserInfoData)dbProcessor.readUserInfo(request.getUsername(), request.getPassword(), conn);
			String authorizeString = null;
			if(data.getPermission() == Permission.Admin) {
				authorizeString = data.getUserName() + Utilities.createRamdomString(10);
				data.setAuthenticationString(authorizeString);
				dbProcessor.AuthorizedUser.put(data.getUserName(), authorizeString);
			}
			AuthenticationResponse response = new AuthenticationResponse((byte)data.getPermission(), authorizeString);
			return response;
		} catch (SQLException sqe) {
			return new ExceptionResponse(IProtocolMessage.IOFailure);
		} catch (SensoryException se) {
			return new ExceptionResponse(IProtocolMessage.NoData);
		}
	}
	
	private static IProtocolMessage userRegister(AuthenticationRequest request, Connection conn, SensoryDataBaseProcessor dbProcessor) {
		try {
			UserInfoData data = new UserInfoData(request.getUsername(), request.getPassword());
			dbProcessor.addUserInfo(data, conn);
			return new AuthenticationResponse(Permission.Guest);
		} catch (SQLException sqe) {
			if (sqe.getErrorCode() == 2627)
				return new ExceptionResponse(IProtocolMessage.UserExisted);
			else
				return new ExceptionResponse(IProtocolMessage.IOFailure);
		}
	}
	
	private static IProtocolMessage userLogOff(AuthenticationRequest request, Connection conn, SensoryDataBaseProcessor dbProcessor) {
		if(!dbProcessor.AuthorizedUser.containsKey(request.getUsername()))
			return new ExceptionResponse(IProtocolMessage.NoData);
		dbProcessor.AuthorizedUser.remove(request.getUsername());
		return new AuthenticationResponse(Permission.Guest);
	}
	
	private static IProtocolMessage handle(DeleteAllHistoryRequest request, Connection conn, SensoryDataBaseProcessor dbProcessor) {
		boolean isSuccess = false;
		try {
			dbProcessor.delete(request.getStart(), request.getEnd(), conn);
			isSuccess = true;
		} catch (Exception e) {
			isSuccess = false;
		}
		return new DeleteHistoryResponse(request.getDataType(), isSuccess);
	}
	
	private static IProtocolMessage handle(DeleteNodeHistoryRequest request, Connection conn, SensoryDataBaseProcessor dbProcessor) {
		boolean isSuccess = false;
		try {
			dbProcessor.delete(request.getNodeId(), request.getStart(), request.getEnd(), conn);
			isSuccess = true;
		} catch (Exception e) {
			isSuccess = false;
		}
		return new DeleteHistoryResponse(request.getDataType(), isSuccess);
	}
	
	private static IProtocolMessage handle(ReadActivedNodesRequest request, Connection conn, SensoryDataBaseProcessor dbProcessor) {
		try {
			IData[] datas = dbProcessor.readBasicInfo(request.getStart(), conn);
			return new ReadActivedNodesResponse(transfer(datas));
		} catch (SQLException e) {
			return new ExceptionResponse(IProtocolMessage.IOFailure);
		} catch (SensoryException e) {
			return new ExceptionResponse(IProtocolMessage.NoData);
		}
	}
	
	private static IProtocolMessage handle(ReadLastInformationRequest request, Connection conn, SensoryDataBaseProcessor dbProcessor) {
		try {
			IData[] datas = dbProcessor.readLastRecord(request.getStart(), request.getNodeIds(), conn);
			return new ReadLastInformationResponse(transfer(datas));
		} catch (SQLException e) {
			return new ExceptionResponse(IProtocolMessage.IOFailure);
		} catch (SensoryException e) {
			return new ExceptionResponse(IProtocolMessage.NoData);
		}
	}
	
	private static IProtocolMessage handle(ReadNodeHistoryRequest request, Connection conn, SensoryDataBaseProcessor dbProcessor) {
		try {
			IData[] datas = dbProcessor.readNodeRecord(request.getNodeId(), request.getStart(), request.getEnd(), conn);
			return new ReadNodeHistoryResponse(request.getNodeId(), transfer(datas));
		} catch (SQLException e) {
			return new ExceptionResponse(IProtocolMessage.IOFailure);
		} catch (SensoryException e) {
			return new ExceptionResponse(IProtocolMessage.NoData);
		}
	}
	
	private static IProtocolMessage handle(ReadNodesHistoryErrorRequest request, Connection conn, SensoryDataBaseProcessor dbProcessor) {
		try {
			IData[] datas = dbProcessor.readNodeError(request.getStart(), request.getEnd(), request.getNodeIds(), conn);
			return new ReadNodesHistoryErrorResponse(transfer(datas));
		} catch (SQLException e) {
			return new ExceptionResponse(IProtocolMessage.IOFailure);
		} catch (SensoryException e) {
			return new ExceptionResponse(IProtocolMessage.NoData);
		}
	}
	
	private static Object[] transfer(IData[] datas) {
		Vector<Object> responseDatas = new Vector<>(); 
		for (IData iData : datas) {
			Data d = (Data)iData;
			if(d == null)
				throw new IllegalArgumentException("class must extends Data");
			Object[] objects = d.format();
			for (Object o : objects) {
				responseDatas.add(o);
			}
		}
		return responseDatas.toArray();
	}
}
