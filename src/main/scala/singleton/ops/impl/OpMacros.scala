package singleton.ops.impl
import scala.reflect.macros.whitebox


trait OpIntercept[N, S1, S2, S3] extends OpMacro[N, S2, S2, S3] {
  import singleton.ops.W

  type OutWide = None.type
  val valueWide: OutWide = None
  val isLiteral: Boolean = false
  type OutNat = shapeless.Nat._0
  type OutChar = W.`'0'`.T
  type OutInt = W.`0`.T
  type OutLong = W.`0L`.T
  type OutFloat = W.`0f`.T
  type OutDouble = W.`0D`.T
  type OutString = W.`""`.T
  type OutBoolean = W.`false`.T
}

object OpIntercept {
  type Aux[N, S1, S2, S3, O] = OpIntercept[N, S1, S2, S3] { type Out = O }
}

/********************************************************************************************************
  * Three arguments type function macro
  *******************************************************************************************************/
@scala.annotation.implicitNotFound("Literal operation has failed.")
trait OpMacro[N, S1, S2, S3] extends Op

object OpMacro {
  type Aux[
  N, 
  S1, 
  S2, 
  S3,
  OutWide0,
  Out0,
  OutNat0 <: shapeless.Nat,
  OutChar0 <: Char with Singleton,
  OutInt0 <: Int with Singleton,
  OutLong0 <: Long with Singleton,
  OutFloat0 <: Float with Singleton,
  OutDouble0 <: Double with Singleton,
  OutString0 <: String with Singleton,
  OutBoolean0 <: Boolean with Singleton
  ] = OpMacro[N, S1, S2, S3] {
    type OutWide = OutWide0
    type Out = Out0
    type OutNat = OutNat0
    type OutChar = OutChar0
    type OutInt = OutInt0
    type OutLong = OutLong0
    type OutFloat = OutFloat0
    type OutDouble = OutDouble0
    type OutString = OutString0
    type OutBoolean = OutBoolean0
  }
  
  implicit def call[
    N,
    S1,
    S2,
    S3,
    OutWide,
    Out,
    OutNat <: shapeless.Nat,
    OutChar <: Char with Singleton,
    OutInt <: Int with Singleton,
    OutLong <: Long with Singleton,
    OutFloat <: Float with Singleton,
    OutDouble <: Double with Singleton,
    OutString <: String with Singleton,
    OutBoolean <: Boolean with Singleton
  ]: Aux[
    N,
    S1,
    S2,
    S3,
    OutWide,
    Out,
    OutNat,
    OutChar,
    OutInt,
    OutLong,
    OutFloat,
    OutDouble,
    OutString,
    OutBoolean
  ] = macro Macro.impl[N, S1, S2, S3]

  final class Macro(val c: whitebox.Context) extends GeneralMacros {
    def impl[
        N : c.WeakTypeTag,
        S1: c.WeakTypeTag,
        S2: c.WeakTypeTag,
        S3: c.WeakTypeTag
    ]: c.Tree =
      materializeOpGen[OpMacro[N, S1, S2, S3]].usingFuncName
  }

//  implicit def valueOfOp[N, S1 : ValueOf, S2 : ValueOf, S3 : ValueOf]
//  (implicit op : OpMacro[N, S1, S2, S3]) : ValueOf[OpMacro[N, S1, S2, S3]] = new ValueOf(op)
}
/*******************************************************************************************************/
