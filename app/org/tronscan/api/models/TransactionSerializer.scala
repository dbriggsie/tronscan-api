package org
package tronscan.api.models

import io.circe.syntax._
import io.circe.{Encoder, Json => Js}
import org.joda.time.DateTime
import org.tron.common.utils.{Base58, ByteArray, Crypto}
import org.tron.protos.Tron.Transaction
import org.tron.protos.Tron.Transaction.Contract.ContractType.{AccountCreateContract, AccountUpdateContract, AssetIssueContract, DeployContract, FreezeBalanceContract, ParticipateAssetIssueContract, TransferAssetContract, TransferContract, UnfreezeAssetContract, UnfreezeBalanceContract, UpdateAssetContract, VoteAssetContract, VoteWitnessContract, WithdrawBalanceContract, WitnessCreateContract, WitnessUpdateContract}
import org.tronscan.Extensions._
import org.tronscan.protocol.TestNetFormatter

object TransactionSerializer {

  implicit val addressFormatter = new TestNetFormatter

  implicit val encodeAssetIssueContract = new Encoder[org.tron.protos.Contract.AssetIssueContract] {
    def apply(assetIssueContract: org.tron.protos.Contract.AssetIssueContract): Js = Js.obj(
      "ownerAddress" -> Base58.encode58Check(assetIssueContract.ownerAddress.toByteArray).asJson,
      "name" -> new String(assetIssueContract.name.toByteArray).trim.asJson,
      "totalSupply" -> assetIssueContract.totalSupply.asJson,
      "trxNum" -> assetIssueContract.trxNum.asJson,
      "num" -> assetIssueContract.num.asJson,
      "startTime" -> new DateTime(assetIssueContract.startTime).asJson,
      "endTime" -> new DateTime(assetIssueContract.endTime).asJson,
      "voteScore" -> assetIssueContract.voteScore.asJson,
      "description" -> new String(assetIssueContract.description.toByteArray).asJson,
      "url" -> new String(assetIssueContract.url.toByteArray).asJson,
    )
  }

  implicit val encodeUpdateAssetContract = new Encoder[org.tron.protos.Contract.UpdateAssetContract] {
    def apply(contract: org.tron.protos.Contract.UpdateAssetContract): Js = Js.obj(
      "ownerAddress" -> Base58.encode58Check(contract.ownerAddress.toByteArray).asJson,
      "newPublicLimit" -> contract.newPublicLimit.asJson,
      "newLimit" -> contract.newLimit.asJson,
      "description" -> new String(contract.description.toByteArray).asJson,
      "url" -> new String(contract.url.toByteArray).asJson,
    )
  }

  implicit val encodeTransferContract = new Encoder[org.tron.protos.Contract.TransferContract] {
    def apply(transferContract: org.tron.protos.Contract.TransferContract): Js = Js.obj(
      "from" -> Base58.encode58Check(transferContract.ownerAddress.toByteArray).asJson,
      "to" -> Base58.encode58Check(transferContract.toAddress.toByteArray).asJson,
      "amount" -> transferContract.amount.asJson,
    )
  }


  implicit val encodeTransferAssetContract = new Encoder[org.tron.protos.Contract.TransferAssetContract] {
    def apply(transferAssetContract: org.tron.protos.Contract.TransferAssetContract): Js = Js.obj(
      "from" -> Base58.encode58Check(transferAssetContract.ownerAddress.toByteArray).asJson,
      "to" -> Base58.encode58Check(transferAssetContract.toAddress.toByteArray).asJson,
      "amount" -> transferAssetContract.amount.asJson,
      "token" -> new String(transferAssetContract.assetName.toByteArray).asJson
    )
  }


  implicit val encodeParticipateAssetIssueContract = new Encoder[org.tron.protos.Contract.ParticipateAssetIssueContract] {
    def apply(participateAssetIssueContract: org.tron.protos.Contract.ParticipateAssetIssueContract): Js = Js.obj(
      "ownerAddress" -> Base58.encode58Check(participateAssetIssueContract.ownerAddress.toByteArray).asJson,
      "toAddress" -> Base58.encode58Check(participateAssetIssueContract.toAddress.toByteArray).asJson,
      "amount" -> participateAssetIssueContract.amount.asJson,
      "token" -> new String(participateAssetIssueContract.assetName.toByteArray).asJson,
    )
  }


  implicit val encodeWitnessUpdateContract = new Encoder[org.tron.protos.Contract.WitnessUpdateContract] {
    def apply(witnessUpdateContract: org.tron.protos.Contract.WitnessUpdateContract): Js = Js.obj(
      "ownerAddress" -> Base58.encode58Check(witnessUpdateContract.ownerAddress.toByteArray).asJson,
      "url" -> new String(witnessUpdateContract.updateUrl.toByteArray).asJson,
    )
  }

  implicit val encodeWitnessCreateContract = new Encoder[org.tron.protos.Contract.WitnessCreateContract] {
    def apply(contract: org.tron.protos.Contract.WitnessCreateContract): Js = Js.obj(
      "ownerAddress" -> Base58.encode58Check(contract.ownerAddress.toByteArray).asJson,
      "url" -> new String(contract.url.toByteArray).asJson,
    )
  }

  implicit val encodeVoteWitnessContract = new Encoder[org.tron.protos.Contract.VoteWitnessContract] {
    def apply(voteWitnessContract: org.tron.protos.Contract.VoteWitnessContract): Js = Js.obj(
      "ownerAddress" -> voteWitnessContract.ownerAddress.toAddress.asJson,
      "votes" -> voteWitnessContract.votes.map { vote =>
        Js.obj(
          "voteAddress" -> vote.voteAddress.toAddress.asJson,
          "voteCount" -> vote.voteCount.asJson,
        )
      }.asJson
    )
  }

  implicit val encodeAccountUpdateContract = new Encoder[org.tron.protos.Contract.AccountUpdateContract] {
    def apply(accountUpdateContract: org.tron.protos.Contract.AccountUpdateContract): Js = Js.obj(
      "ownerAddress" -> Base58.encode58Check(accountUpdateContract.ownerAddress.toByteArray).asJson,
      "name" -> new String(accountUpdateContract.accountName.toByteArray).asJson,
    )
  }

  implicit val encodeAccountCreateContract = new Encoder[org.tron.protos.Contract.AccountCreateContract] {
    def apply(contract: org.tron.protos.Contract.AccountCreateContract): Js = Js.obj(
      "ownerAddress" -> Base58.encode58Check(contract.ownerAddress.toByteArray).asJson,
      "accountAddress" -> new String(contract.accountAddress.toByteArray).asJson,
      "type" -> contract.`type`.value.asJson,
    )
  }

  implicit val encodeVoteAssetContract = new Encoder[org.tron.protos.Contract.VoteAssetContract] {
    def apply(contract: org.tron.protos.Contract.VoteAssetContract): Js = Js.obj(
      "ownerAddress" -> Base58.encode58Check(contract.ownerAddress.toByteArray).asJson,
    )
  }

  implicit val encodeFreezeBalanceContract = new Encoder[org.tron.protos.Contract.FreezeBalanceContract] {
    def apply(contract: org.tron.protos.Contract.FreezeBalanceContract): Js = Js.obj(
      "ownerAddress" -> Base58.encode58Check(contract.ownerAddress.toByteArray).asJson,
      "frozenDuration" -> contract.frozenDuration.asJson,
      "frozenBalance" -> contract.frozenBalance.asJson,
    )
  }

  implicit val encodeUnfreezeBalanceContract = new Encoder[org.tron.protos.Contract.UnfreezeBalanceContract] {
    def apply(contract: org.tron.protos.Contract.UnfreezeBalanceContract): Js = Js.obj(
      "ownerAddress" -> Base58.encode58Check(contract.ownerAddress.toByteArray).asJson,
    )
  }

  implicit val encodeWithdrawBalanceContract = new Encoder[org.tron.protos.Contract.WithdrawBalanceContract] {
    def apply(contract: org.tron.protos.Contract.WithdrawBalanceContract): Js = Js.obj(
      "ownerAddress" -> Base58.encode58Check(contract.ownerAddress.toByteArray).asJson,
    )
  }

  implicit val encodeUnfreezeAssetContract = new Encoder[org.tron.protos.Contract.UnfreezeAssetContract] {
    def apply(contract: org.tron.protos.Contract.UnfreezeAssetContract): Js = Js.obj(
      "ownerAddress" -> Base58.encode58Check(contract.ownerAddress.toByteArray).asJson,
    )
  }
  implicit val encodeDeployContract = new Encoder[org.tron.protos.Contract.DeployContract] {
    def apply(contract: org.tron.protos.Contract.DeployContract): Js = Js.obj(
      "ownerAddress" -> Base58.encode58Check(contract.ownerAddress.toByteArray).asJson,
      "script" -> ByteArray.toHexString(contract.script.toByteArray).asJson,
    )
  }

  def serializeContract(contract: Transaction.Contract, includeType: Boolean = false) = {
    val contractJson = contract.`type` match {
      case AccountCreateContract =>
        org.tron.protos.Contract.AccountCreateContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case TransferContract =>
        org.tron.protos.Contract.TransferContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case TransferAssetContract =>
        org.tron.protos.Contract.TransferAssetContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case VoteAssetContract =>
        org.tron.protos.Contract.VoteAssetContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case VoteWitnessContract =>
        org.tron.protos.Contract.VoteWitnessContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case AssetIssueContract =>
        org.tron.protos.Contract.AssetIssueContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case DeployContract =>
        org.tron.protos.Contract.DeployContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case ParticipateAssetIssueContract =>
        org.tron.protos.Contract.ParticipateAssetIssueContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case WitnessCreateContract =>
        org.tron.protos.Contract.WitnessCreateContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case WitnessUpdateContract =>
        org.tron.protos.Contract.WitnessUpdateContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case FreezeBalanceContract =>
        org.tron.protos.Contract.FreezeBalanceContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case UnfreezeBalanceContract =>
        org.tron.protos.Contract.UnfreezeBalanceContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case AccountUpdateContract =>
        org.tron.protos.Contract.AccountUpdateContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case WithdrawBalanceContract =>
        org.tron.protos.Contract.WithdrawBalanceContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case UnfreezeAssetContract =>
        org.tron.protos.Contract.UnfreezeAssetContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case UpdateAssetContract =>
        org.tron.protos.Contract.UpdateAssetContract.parseFrom(contract.getParameter.value.toByteArray).asJson

      case _ =>
        Js.obj(
          "type" -> contract.`type`.toString().asJson
        )
    }


    if (includeType) {
      contractJson
        .deepMerge(Js.obj(
          "contractType" -> contract.`type`.toString().asJson,
          "contractTypeId" -> contract.`type`.value.asJson
        ))
    } else {
      contractJson
    }
  }

  def serialize(transaction: Transaction) = Js.obj(
    "timestamp" -> transaction.getRawData.timestamp.asJson,
    "contracts" -> transaction.getRawData.contract.map(contract => serializeContract(contract, includeType = true)).asJson,
    "signatures" -> transaction.signature.map { signature =>
      Js.obj(
        "bytes" -> Crypto.getBase64FromByteString(signature).asJson,
      )
    }.asJson,
  )
}
