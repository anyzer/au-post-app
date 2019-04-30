package bran.post.config

import bran.post.helper.Env
import com.typesafe.config.{Config, ConfigFactory}

case class PostConfig(base_url: String, authorization: String, account: String,
                      eparcel_acc: String, same_day_acc: String, st_acc: String,
                      read_path: String, write_path: String)

object PostConfig {

  def get(setting: Env): PostConfig = {
    val configs: Config = ConfigFactory.load("config.conf")

    PostConfig(configs.getString(s"settings.${setting.env}.base_url"),
            configs.getString(s"settings.${setting.env}.authorization"),
            configs.getString(s"settings.${setting.env}.account"),
            configs.getString(s"settings.${setting.env}.eparcel_account"),
            configs.getString(s"settings.${setting.env}.same_day_account"),
            configs.getString(s"settings.${setting.env}.st_account"),
            configs.getString(s"settings.${setting.env}.read_path"),
            configs.getString(s"settings.${setting.env}.write_path")

    )
  }
}
