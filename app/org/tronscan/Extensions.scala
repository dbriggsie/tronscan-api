package org.tronscan

import com.google.protobuf.ByteString
import org.tron.common.utils.{Base58, Sha256Hash}
import org.tron.protos.Tron.{Block, Transaction}

object Extensions {

  implicit class ImplicitBlock(block: Block) {
    def hash: String = Sha256Hash.of(block.getBlockHeader.getRawData.toByteArray).toString
    def hashBytes = Sha256Hash.of(block.getBlockHeader.getRawData.toByteArray).getBytes
    def number: Long = block.getBlockHeader.getRawData.number
  }

  implicit class ImplicitTransaction(trx: Transaction) {
    def hash: String = Sha256Hash.of(trx.getRawData.toByteArray).toString
  }

  implicit class ByteStringUtils(byteString: ByteString) {
    def toAddress = {
      Base58.encode58Check(byteString.toByteArray)
    }

  }

}
